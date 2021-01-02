package ru.itis;

import java.util.*;

class CityGame {

    public static void main(String[] args) throws IllegalAccessException {
        List<String> words = Arrays.asList("казан", "ньюйорк", "нижнийтагил");
        Map<Character, List<Edge>> graph = graphCreation(words);
        if (!EulerChecker.checkForEulerPath(graph)) {
            throw new IllegalAccessException("Game can not be ended success");
        }

        Character startVertex = findStartVertex(graph);
        List<Character> vertexOrder = findEulerPath(graph, startVertex);
        if (vertexOrder.size() - 1 != words.size()) {
            throw new IllegalAccessException("Game can not be ended success");
        }
        List<String> wordOrder = getWordOrder(vertexOrder, graph);
        System.out.println(wordOrder);

    }

    public static List<String> getWordOrder(List<Character> vertexOrder, Map<Character, List<Edge>> graph) {
        List<String> wordOrder = new ArrayList<>();
        for (int i = 0; i < vertexOrder.size() - 1; i++) {
            Character startVertex = vertexOrder.get(i);
            Character endVertex = vertexOrder.get(i + 1);
            Edge edge = graph.get(startVertex).stream()
                    .filter(Edge::isDeleted)
                    .filter(e -> e.getWord().charAt(e.getWord().length() - 1) == endVertex)
                    .findAny()
                    .get();
            edge.setDeleted(false);
            wordOrder.add(edge.getWord());
        }
        return wordOrder;
    }

    public static List<Character> findEulerPath(Map<Character, List<Edge>> graph, Character startVertex) {
        List<Character> wordOrder = new ArrayList<>();
        Deque<Character> stack = new LinkedList<>();
        stack.push(startVertex);
        while (!stack.isEmpty()) {
            Character currentVertex = stack.peek();
            boolean findIntermediateVertex = false;
            for (Edge edge : graph.get(currentVertex)) {
                if (!edge.isDeleted()) {
                    edge.setDeleted();
                    stack.push(edge.getTo());
                    findIntermediateVertex = true;
                    break;
                }
            }
            if (!findIntermediateVertex) {
                wordOrder.add(stack.pop());
            }
        }

        Collections.reverse(wordOrder);
        return wordOrder;
    }

    public static Character findStartVertex(Map<Character, List<Edge>> graph) {
        for (Character vertex : graph.keySet()) {
            EulerChecker.Deg deg = EulerChecker.deg(vertex, graph);
            if (deg.getOutCount() - deg.getInCount() == 1) {
                return vertex;
            }
        }

        return graph.keySet().stream()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No vertex in graph"));

    }

    public static Map<Character, List<Edge>> graphInit(List<String> words) {
        Map<Character, List<Edge>> graph = new HashMap<>();
        words.forEach(word -> {
            Character startVertex = word.charAt(0);
            Character endVertex = word.charAt(word.length() - 1);
            graph.computeIfAbsent(startVertex, k -> new ArrayList<>());
            graph.computeIfAbsent(endVertex, k -> new ArrayList<>());
        });
        return graph;
    }

    public static Map<Character, List<Edge>> graphCreation(List<String> words) {
        Map<Character, List<Edge>> graph = graphInit(words);
        words.forEach(word -> {
            char vertex = word.charAt(0);
            graph.computeIfAbsent(vertex, k -> new ArrayList<>());
            List<Edge> list = graph.get(vertex);
            list.add(new Edge(word.charAt(word.length() - 1), word, false));
        });
        return graph;
    }

    public static class Edge {
        public Character getTo() {
            return to;
        }

        public String getWord() {
            return word;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        private Character to;
        private String word;
        private boolean deleted;

        public Edge(Character to, String word, boolean deleted) {
            this.to = to;
            this.word = word;
            this.deleted = deleted;
        }

        public void setDeleted() {
            deleted = true;
        }
    }
}

class EulerChecker {

    public static boolean checkForEulerPath(Map<Character, List<CityGame.Edge>> graph) {
        List<Deg> vertexDeg = new ArrayList<>();
        graph.keySet().forEach(vertex -> vertexDeg.add(deg(vertex, graph)));
        long oddVertex = vertexDeg.stream()
                .filter(deg -> deg.getDegSum() % 2 != 0)
                .count();

        return oddVertex <= 2 && (vertexDeg.stream().anyMatch(Deg::isStartVertex) && vertexDeg.stream().anyMatch(Deg::isEndVertex));
    }

    public static Deg deg(Character vertex, Map<Character, List<CityGame.Edge>> graph) {
        int outCount = graph.get(vertex).size();
        int inCount = (int) graph.values().stream()
                .flatMap(List::stream)
                .filter(word -> word.getTo().equals(vertex))
                .count();
        return new Deg(outCount, inCount);
    }

    public static class Deg {
        public int getInCount() {
            return inCount;
        }

        public int getOutCount() {
            return outCount;
        }

        private int inCount;
        private int outCount;

        public Deg(int inCount, int outCount) {
            this.inCount = inCount;
            this.outCount = outCount;
        }

        public int getDegSum() {
            return inCount + outCount;
        }

        public boolean isStartVertex() {
            return outCount - inCount == 1;
        }

        public boolean isEndVertex() {
            return inCount - outCount == 1;
        }
    }
}
