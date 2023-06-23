package com.sse.publisher.constants;

public abstract class StatusConstants {

    private StatusConstants() {}

    public static final String STATUS_200_GET_OK = "Successfully retrieved";
    public static final String STATUS_201_CREATED = "Successfully created";

    public static final String STATUS_204_NO_CONTENT = "No Content";

    public static final String STATUS_400_BAD_REQUEST = "Resource is invalid";

    public static final String STATUS_401_UNAUTHORIZED =
            "You are not authorized to view the resource";

    public static final String STATUS_403_FORBIDDEN =
            "Accessing the resource you were trying to reach is forbidden";

    public static final String STATUS_404_NOT_FOUND =
            "The resource you were trying to reach is not found";

    public static final String STATUS_503_UNAVAILABLE =
            "Service Unavailable. This error can be a result of queue infrastructure not available. In this case, nothing will be processed.";

    public static final String STATUS_500_UNAVAILABLE =
            "Internal Server error. In this case, nothing will be processed.";
}
