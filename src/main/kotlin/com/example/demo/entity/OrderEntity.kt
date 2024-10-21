package com.example.demo.entity


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.yaml.snakeyaml.constructor.Constructor
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener::class)
data class OrderEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_orders_id")
    @SequenceGenerator(name = "seq_orders_id", sequenceName = "seq_orders_id", allocationSize = 50)
    var id: Int?,

    var customerId: Int?,

    var productId: Int?,

    var productCount: Int?,

    var price: Int?,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    var createdDate: LocalDateTime?,

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "updated_date")
    var updatedDate: LocalDateTime?,

    var endDate: LocalDate?,

    var endTime: LocalTime?
)
{
    constructor() : this(null, null, null,
        null, null,
        null, null, null, null)
}
