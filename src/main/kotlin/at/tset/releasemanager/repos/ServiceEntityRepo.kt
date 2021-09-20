package at.tset.releasemanager.repos

import at.tset.releasemanager.entities.ServiceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

interface ServiceEntityRepo : JpaRepository<ServiceEntity, UUID> {

    @Query(
        """
        select case when count(s) > 0 then true else false end
        from ServiceEntity s
        where s.version=:version
        and s.name=:name
        """
    )
    fun existsService(@Param("name")
                      name: String,
                      @Param("version")
                      version: Int
    ): Boolean

    @Query(
        """
        select max(s.commissionedSystemVersion)
        from ServiceEntity s
        """
    )
    fun fetchCurrentSystemVersion(): Int?

    @Query(
        """
        select s
        from ServiceEntity s
        where s.commissionedSystemVersion <= :systemVersion
        and (s.decommissionedSystemVersion is null or s.decommissionedSystemVersion > :systemVersion )
        """
    )
    fun fetchServicesForSystemVersion(@Param("systemVersion")
    systemVersion: Int
    ): List<ServiceEntity>

    @Query(
        """
        update ServiceEntity s
        set s.decommissionedSystemVersion = :decommissioningVersion
        where s.name=:name
        and s.decommissionedSystemVersion is null
        """
    )
    @Modifying
    fun decommissionPreviousService(@Param("name")
                                    name: String,
                                    @Param("decommissioningVersion")
                                    decommissioningVersion: Int
    ): Int

}