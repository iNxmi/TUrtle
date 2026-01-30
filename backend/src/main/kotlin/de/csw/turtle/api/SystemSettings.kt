//package de.csw.turtle.api
//
//import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
//import de.csw.turtle.api.entity.SystemSettingEntity
//import de.csw.turtle.api.service.SystemSettingService
//import java.time.LocalTime
//
//sealed interface SystemSettings {
//
//    object CALENDAR_TIME_START  {
//        val key = "calendar.time.start"
//        val default: LocalTime = LocalTime.of(6, 0)
//        fun get(service: SystemSettingService): LocalTime = service.getTypedOrDefault<LocalTime>(key, default)
//    }
//
//    object CALENDAR_TIME_END : SystemSetting<LocalTime> {
//        override val key = "calendar.time.end"
//        override val default: LocalTime = LocalTime.of(22, 0)
//        override fun get(service: SystemSettingService): LocalTime = service.getTypedOrDefault<LocalTime>(key, default)
//    }
//
//    object TEMPLATE_IMPRINT : SystemSetting<LocalTime> {
//        override val key = "template.imprint"
//        override val default: LocalTime = LocalTime.of(22, 0)
//        override fun get(service: SystemSettingService): LocalTime = service.getTypedOrDefault<LocalTime>(key, default)
//    }
//
//    if(service.getByKeyOrNull("template.imprint") == null) {
//        val template = templateService.getByName("imprint")
//        service.create(CreateSystemSettingRequest("template.imprint", SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE, template.id.toString()))
//    }
//
//    if(service.getByKeyOrNull("template.gdpr") == null) {
//        val template = templateService.getByName("gdpr")
//        service.create(CreateSystemSettingRequest("template.gdpr", SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE, template.id.toString()))
//    }
//
//    if(service.getByKeyOrNull("template.tos") == null) {
//        val template = templateService.getByName("tos")
//        service.create(CreateSystemSettingRequest("template.tos", SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE, template.id.toString()))
//    }
//
//    if(service.getByKeyOrNull("template.contact") == null) {
//        val template = templateService.getByName("contact")
//        service.create(CreateSystemSettingRequest("template.contact", SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE, template.id.toString()))
//    }
//
//    if(service.getByKeyOrNull("template.about") == null) {
//        val template = templateService.getByName("about")
//        service.create(CreateSystemSettingRequest("template.about", SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE, template.id.toString()))
//    }
//
//}