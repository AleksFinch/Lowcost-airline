package com.finchuk.dto;

/**
 * Created by olexandr on 25.03.17.
 */
public enum TicketStatus {
    FREE {
        @Override
        public int getValue() {
            return 0;
        }
    },
    CHECK_PAYMENT {
        @Override
        public int getValue() {
            return 1;
        }
    },
    PAID {
        @Override
        public int getValue() {
            return 2;
        }
    };

    public abstract int getValue();
}
