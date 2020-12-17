/*
package ru.itis.experiments;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EulerChecker {

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
        return Deg.builder()
                .outCount(outCount)
                .inCount(inCount)
                .build();
    }

    @Data
    @Builder
    public static class Deg {
        private int inCount;
        private int outCount;

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

public class Domino {

    public static void main(String[] args) throws IllegalAccessException {
        List<Pair> data = Arrays.asList(
                new Pair(1, 3),
                new Pair(1, 3),
                new Pair(4, 5),
                new Pair(3, 4),
                new Pair(5, 6),
                new Pair(6, 1),
                new Pair(6, 6)
        );
//        List<Pair> data = Arrays.asList(
//                new Pair(1, 3),
//                new Pair(3, 5),
//                new Pair(5, 6)
//        );

        List<List<Edge>> graph = getGraph(data);

        int startVertex = findStartVertex(graph);

        List<Integer> result = findEulerPath(graph, startVertex);
        if (result.size() - 1 != data.size()) {
            throw new IllegalAccessException("Can not win game");
        }

        for (int i = 0; i < result.size() - 1; i++) {
            System.out.println("(" + result.get(i) + " | " + result.get(i + 1) + ")");
        }

    }

    public static List<Integer> findEulerPath(List<List<Edge>> graph, int startVertex) {
        List<Integer> dominoOrder = new ArrayList<>();
        Deque<Integer> stack = new LinkedList<>();
        stack.push(startVertex);
        while (!stack.isEmpty()) {
            Integer currentVertex = stack.peek();
            boolean findIntermediateVertex = false;
            for (Edge edge : graph.get(currentVertex)) {
                if (!edge.isDeleted()) {
                    stack.push(edge.to);
                    edge.setDeleted(true);
                    graph.get(edge.to).stream()
                            .filter(e -> e.to == currentVertex && !e.isDeleted())
                            .findAny()
                            .get()
                            .setDeleted(true);
                    findIntermediateVertex = true;
                    break;
                }
            }
            if (!findIntermediateVertex) {
                dominoOrder.add(stack.pop());
            }
        }

        return dominoOrder;
    }

    private static List<List<Edge>> getGraph(List<Pair> data) {
        List<List<Edge>> graph = new ArrayList<>();
        int maxVertex = 0;
        for (Pair e : data) {
            maxVertex = Integer.max(maxVertex, Integer.max(e.first, e.second));
        }

        for (int i = 0; i <= maxVertex; i++) {
            graph.add(new ArrayList<>());
        }

        data.forEach(edge -> {
            graph.get(edge.getSecond()).add(new Edge(edge.getFirst(), false));
            graph.get(edge.getFirst()).add(new Edge(edge.getSecond(), false));
        });

        return graph;
    }


    private static int findStartVertex(List<List<Edge>> graph) throws IllegalAccessException {
        List<Integer> oddVertex = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++) {
            int deg = deg(i, graph);
            if (deg % 2 != 0) {
                oddVertex.add(i);
            }
        }
        if (oddVertex.size() > 2) {
            throw new IllegalAccessException("Can not win game");
        }

        if (oddVertex.size() == 0) {
            return graph.stream()
                    .flatMap(List::stream)
                    .filter(e -> deg(e.to, graph) != 0)
                    .map(Edge::getTo)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No vertex in graph"));
        }

        return oddVertex.stream().findAny().get();
    }


    private static int deg(int vertex, List<List<Edge>> graph) {
        return graph.get(vertex).size();
    }

    @Data
    @AllArgsConstructor
    public static class Edge {
        private int to;
        private boolean deleted;
    }

    @Data
    @AllArgsConstructor
    public static class Pair {
        private int first;
        private int second;
    }

}
*/
