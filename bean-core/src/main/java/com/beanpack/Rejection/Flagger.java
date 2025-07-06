package com.beanpack.Rejection;

import com.beanpack.Utils.MetaHelper;
import com.beanpack.logger.PackLoggerManager;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;

import com.beanpack.TXs.TX;

public class Flagger {
    public static String rFlag  = "[REJECTION] ";
    
    public static TX repackRejection(TX tx, String reason) {
        try {
            ObjectNode meta = MetaHelper.getMetaObject(tx);
            int n = 0;

            // Count existing rejection flags
            Iterator<String> fieldNames = meta.fieldNames();
            while (fieldNames.hasNext()) {
                String field = fieldNames.next();
                if (field.startsWith("rejectR")) {
                    n++;
                }
            }

            // Pack new rejection reason
            String key = "rejectR" + n;
            String rFlag = "[REJECT] "; 
            meta.put(key, rFlag + reason);

            // Save updated meta back to TX
            tx.setMeta(meta.toString());
            return tx;

        } catch (Exception e) {
            PackLoggerManager.PackLoggerError("EXCEPTION in repackRejection: " + e.getMessage());
            return tx; 
        }
    }
}
