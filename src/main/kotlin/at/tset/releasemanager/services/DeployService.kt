package at.tset.releasemanager.services

import at.tset.releasemanager.dto.ServiceDto
import at.tset.releasemanager.entities.ServiceEntity
import at.tset.releasemanager.repos.ServiceEntityRepo
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.transaction.Transactional

private val logger = KotlinLogging.logger {}

@Service
class DeployService(private val serviceEntityRepo: ServiceEntityRepo
) {

    @Transactional
    //TODO discuss version sequence
    fun deployNewService(service: ServiceDto): Int {
        val currentSystemVersion = serviceEntityRepo.fetchCurrentSystemVersion() ?: 0
        val exists = serviceEntityRepo.existsService(
            service.name,
            service.version
        )
        logger.info(
            "exists[{}]",
            exists
        )
        logger.info(
            "CurrentSystemVersion[{}]",
            currentSystemVersion
        )
        if (exists) return currentSystemVersion

        val newSystemVersion = currentSystemVersion + 1
        val serviceEntity = ServiceEntity(
            name = service.name,
            version = service.version,
            commissionedSystemVersion = newSystemVersion
        )

        val decommissions = serviceEntityRepo.decommissionPreviousService(
            serviceEntity.name,
            newSystemVersion
        )
        logger.info(
            "Decommissioned [{}] service",
            decommissions
        )
        serviceEntityRepo.save(serviceEntity)
        return newSystemVersion
    }

    @Transactional
    fun fetchServicesForSystemVersion(systemVersion: Int): List<ServiceEntity> {
        val services = serviceEntityRepo.fetchServicesForSystemVersion(systemVersion)
        logger.info(
            "Found these services for systemVersion[{}]: [{}]",
            systemVersion,
            services
        )
        return services
    }

}