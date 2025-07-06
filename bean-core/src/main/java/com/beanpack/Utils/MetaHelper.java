package com.beanpack.Utils;

import com.beanpack.TXs.TX;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Helper for working with TX metadata JSON.
 */
public class MetaHelper {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Safely retrieves TX meta as a modifiable ObjectNode.
     * If meta is null or invalid, returns a new empty node.
     */
    public static ObjectNode getMetaObject(TX tx) {
        try {
            if (tx.getMeta() == null || tx.getMeta().isBlank()) {
                return mapper.createObjectNode();
            }
            JsonNode node = mapper.readTree(tx.getMeta());
            return (node.isObject()) ? (ObjectNode) node : mapper.createObjectNode();
        } catch (Exception e) {
            return mapper.createObjectNode(); // fallback on parse error
        }
    }

    /**
     * Adds or updates a key-value pair in TX.meta and saves it back to the TX.
     */
    public static void putMetaValue(TX tx, String key, String value) {
        ObjectNode meta = getMetaObject(tx);
        meta.put(key, value);
        tx.setMeta(meta.toString());
    }

    /**
     * View a specific key in metadata.
     */
    public static String getMetaValue(TX tx, String key) {
        ObjectNode meta = getMetaObject(tx);
        JsonNode value = meta.get(key);
        return value != null ? value.asText() : null;
    }

    /**
     * Returns raw JsonNode (read-only use).
     */
    public static JsonNode getMetaNode(TX tx) {
        return getMetaObject(tx);
    }
}
