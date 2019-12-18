package david.vaz.space.neon.refactored.game;

public enum Direction {
    EAST(1, 0),
    WEST(-1, 0),
    NORTH(0, -1),
    SOUTH(0, 1),
    NORTHEAST(0.8, -0.8),
    SOUTHEAST(0.8, 0.8),
    NORTHWEST(-0.8, -0.8),
    SOUTHWEST(-0.8, 0.8);

    private double x;
    private double y;

    Direction(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private boolean isOpposite(Direction direction) {

        switch (this) {
            case EAST:
                return direction.equals(WEST);
            case WEST:
                return direction.equals(EAST);
            case NORTH:
                return direction.equals(SOUTH);
            case SOUTH:
                return direction.equals(NORTH);
        }

        throw new IllegalStateException();
    }

    public static Direction resolveTwoPressedDirections(Direction direction, Direction secondDirection) {

        if (direction.isOpposite(secondDirection)) {
            return secondDirection;
        }

        if (direction.equals(NORTH) && secondDirection.equals(EAST) ||
                direction.equals(EAST) && secondDirection.equals(NORTH)) {
            return Direction.NORTHEAST;
        }

        if (direction.equals(NORTH) && secondDirection.equals(WEST) ||
                direction.equals(WEST) && secondDirection.equals(NORTH)) {
            return Direction.NORTHWEST;
        }

        if (direction.equals(SOUTH) && secondDirection.equals(EAST) ||
                direction.equals(EAST) && secondDirection.equals(SOUTH)) {
            return Direction.SOUTHEAST;
        }

        if (direction.equals(SOUTH) && secondDirection.equals(WEST) ||
                direction.equals(WEST) && secondDirection.equals(SOUTH)) {
            return Direction.SOUTHWEST;
        }

        throw new IllegalStateException();
    }



}
