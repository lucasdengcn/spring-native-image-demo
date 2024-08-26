package com.example.demo.entity


import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.yaml.snakeyaml.constructor.Constructor

@Entity
@Table(name = "orders")
class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_orders_id")
    @SequenceGenerator(name = "seq_orders_id", sequenceName = "seq_orders_id", allocationSize = 50)
    var id: Int? = null

    val customerId: Int? = null

    val productId: Int? = null
    val productCount: Int? = null

    val price: Int? = null

    constructor()

}
