package main.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    /**
     * Forms an error response.
     *
     * @param code    The HTTP code of the response.
     * @param message The message of the response.
     * @return Error response
     */
    public static Map<String, Object> getErrorResponse(int code, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", message);
        map.put("data", new ArrayList<>());

        return map;
    }
}
