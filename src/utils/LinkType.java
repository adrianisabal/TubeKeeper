package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import java.net.URI;
import java.net.URISyntaxException;


// CHAT-GPT Generated

public enum LinkType {
    VIDEO, PLAYLIST, UNKNOWN;

	public LinkType getLinkType(String url) {
	    try {
	        URI uri = new URI(url);
	        String query = uri.getQuery();
	        if (query == null) return LinkType.UNKNOWN;

	        Map<String, String> params = Arrays.stream(query.split("&"))
	                .map(s -> s.split("=", 2))
	                .filter(arr -> arr.length == 2)
	                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));

	        if (params.containsKey("v") && !params.containsKey("list")) {
	            return LinkType.VIDEO;
	        } else if (params.containsKey("list")) {
	            return LinkType.PLAYLIST;
	        } else {
	            return LinkType.UNKNOWN;
	        }
	    } catch (URISyntaxException e) {
	        return LinkType.UNKNOWN;
	    }
	}
}
