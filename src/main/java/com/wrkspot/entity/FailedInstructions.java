package com.wrkspot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(schema = "wrkSpot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FailedInstructions {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    UUID id;
    String reason;
    String message;
    String topic;
    String messageType;
}
