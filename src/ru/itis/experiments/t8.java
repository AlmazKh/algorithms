/*
package ru.itis.experiments;

public class CityGame {

    public static void main(String[] args) throws IllegalAccessException {
        List<String> words = Arrays.asList("казан", "ньюйорк","нижнийтагил");
        Map<Character, List<Edge>> graph = graphCreation(words);
        if (!EulerChecker.checkForEulerPath(graph)){
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
        for (int i = 0; i < vertexOrder.size() - 1; i  ++) {
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
            list.add(Edge.builder()
                    .deleted(false)
                    .word(word)
                    .to(word.charAt(word.length() - 1))
                    .build());
        });
        return graph;
    }


    @Data
    @Builder
    public static class Edge {
        private Character to;
        private String word;
        private boolean deleted;
        public void setDeleted() {
            deleted = true;
        }
    }

}
*/
