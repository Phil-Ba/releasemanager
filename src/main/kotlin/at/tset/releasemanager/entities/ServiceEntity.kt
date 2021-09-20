package at.tset.releasemanager.entities

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "SERVICE")
class ServiceEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @field:NotBlank
    val name: String,
    @field:Min(1)
    val version: Int,
    @field:Min(1)
    val commissionedSystemVersion:Int,
    @field:Min(1)
    val decommissionedSystemVersion:Int?=null,
) {

    @Version
    private val entityVersion:Long?=null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceEntity

        if (id != other.id) return false
        if (entityVersion != other.entityVersion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + entityVersion.hashCode()
        return result
    }

    override fun toString(): String {
        return "ServiceEntity(id=$id, name='$name', version=$version, commissionedSystemVersion=$commissionedSystemVersion, decommissionedSystemVersion=$decommissionedSystemVersion, entityVersion=$entityVersion)"
    }


}