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

    public static Direction resolveTwoPressedDirections(Direction direction, Direction secondDirection) {

        if (direction.isOpposite(secondDirection)) {
            return secondDirection;
        }

        if (direction == NORTH && secondDirection == EAST ||
                direction == EAST && secondDirection == NORTH) {
            return Direction.NORTHEAST;
        }

        if (direction == NORTH && secondDirection == WEST ||
                direction == WEST && secondDirection == NORTH) {
            return Direction.NORTHWEST;
        }

        if (direction == SOUTH && secondDirection == EAST ||
                direction == EAST && secondDirection == SOUTH) {
            return Direction.SOUTHEAST;
        }

        if (direction == SOUTH && secondDirection == WEST ||
                direction == WEST && secondDirection == SOUTH) {
            return Direction.SOUTHWEST;
        }

        throw new IllegalStateException();
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
                return direction == WEST;
            case WEST:
                return direction == EAST;
            case NORTH:
                return direction == SOUTH;
            case SOUTH:
                return direction == NORTH;
        }

        throw new IllegalStateException();
    }
}
