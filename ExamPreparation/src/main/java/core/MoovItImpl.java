package core;

import models.Route;

import java.util.*;
import java.util.stream.Collectors;

public class MoovItImpl implements MoovIt {

    private final Map<String, Route> routesById;

    private final Set<Route> routes;

    public MoovItImpl() {
        this.routesById = new LinkedHashMap<>();
        this.routes = new HashSet<>();
    }

    @Override
    public void addRoute(Route route) {
        if (this.routes.contains(route)) {
            throw new IllegalArgumentException();
        }
        this.routesById.put(route.getId(), route);
        this.routes.add(route);
    }

    @Override
    public void removeRoute(String routeId) {
        Route result = this.routesById.remove(routeId);

        if (result == null) {
            throw new IllegalArgumentException();
        }

        this.routes.remove(result);
    }

    @Override
    public boolean contains(Route route) {
        return this.routes.contains(route);
    }

    @Override
    public int size() {
        return this.routesById.size();
    }

    @Override
    public Route getRoute(String routeId) {
        Route result = this.routesById.get(routeId);

        if (result == null) {
            throw new IllegalArgumentException();
        }
        return result;
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = this.getRoute(routeId);

        Integer current = route.getPopularity();
        route.setPopularity(current + 1);
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        return routesById
                .values()
                .stream()
                .filter(r -> {
                    List<String> points = r.getLocationPoints();

                    int startIndex = points.indexOf(startPoint);
                    int endIndex = points.indexOf(endPoint);

                    return startIndex > -1 && endIndex > -1 && startIndex < endIndex;
                })
                .sorted((l, r) -> {
                    if (l.getIsFavorite() && !r.getIsFavorite()) {
                        return -1;
                    }
                    if (r.getIsFavorite() && !l.getIsFavorite()) {
                        return 1;
                    }

                    int lDistance = l.getLocationPoints().indexOf(endPoint) - l.getLocationPoints().indexOf(startPoint);
                    int rDistance = r.getLocationPoints().indexOf(endPoint) - r.getLocationPoints().indexOf(startPoint);

                    if (lDistance != rDistance) {
                        return lDistance - rDistance;
                    }
                    return r.getPopularity() - l.getPopularity();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        return this.routesById
                .values()
                .stream()
                .filter(r -> {
                    int pointIndex = r.getLocationPoints().indexOf(destinationPoint);

                    return r.getIsFavorite() && pointIndex > 0;
                })
                .sorted((l, r) -> {
                    if (l.getDistance().equals(r.getDistance())) {
                        return Double.compare(l.getDistance(), r.getDistance());
                    }
                    return r.getPopularity() - l.getPopularity();
                })
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Route> getTop5RoutesByPopularityThenByDistanceThenByCountOfLocationPoints() {
        return this.routesById
                .values()
                .stream()
                .sorted((l, r) -> {
                    if (!l.getPopularity().equals(r.getPopularity())) {
                        return r.getPopularity() - l.getPopularity();
                    }

                    if (!l.getDistance().equals(r.getDistance())) {
                        return Double.compare(l.getDistance(), r.getDistance());
                    }

                    return l.getLocationPoints().size() - r.getLocationPoints().size();
        })
                .limit(5)
                .collect(Collectors.toList());
    }
}
