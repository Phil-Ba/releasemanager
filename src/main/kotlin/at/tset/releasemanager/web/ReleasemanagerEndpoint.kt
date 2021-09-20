package at.tset.releasemanager.web

import at.tset.releasemanager.dto.ServiceDto
import at.tset.releasemanager.entities.ServiceEntity
import at.tset.releasemanager.services.DeployService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/")
class ReleasemanagerEndpoint(private val deployService: DeployService) {

    @PostMapping("deploy")
    fun deploy(@Valid
               @RequestBody
               service: ServiceDto
    ): Int {
        return deployService.deployNewService(service)
    }

    @GetMapping("services")
    fun getServiceListing(
        @RequestParam
        systemVersion: Int
    ): List<ServiceDto> {
        return deployService.fetchServicesForSystemVersion(systemVersion)
            .map {
                ServiceDto(
                    it.name,
                    it.version
                )
            }
    }

}