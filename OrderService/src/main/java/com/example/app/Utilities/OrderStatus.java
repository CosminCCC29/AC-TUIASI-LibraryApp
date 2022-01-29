package com.example.app.Utilities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public enum OrderStatus {
    INITIALIZED,
    PREPARING,
    FINALIZED
}
