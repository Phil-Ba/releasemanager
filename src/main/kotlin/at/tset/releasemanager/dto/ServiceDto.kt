package at.tset.releasemanager.dto

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ServiceDto(
    @field:NotNull
    @field:NotBlank
    val name: String,
    @field:Min(1)
    val version: Int
)