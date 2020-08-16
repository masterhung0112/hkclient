package com.hungknow.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile (
    val id: String = "",
    val create_at: Int = 0,
    val update_at: Int = 0,
    val delete_at: Int = 0,
    val username: String = "",
    val password: String = "",
//    val auth_data: String,
//    val auth_service: String,
    val email: String = "",
    val email_verified: Boolean = false,
//    val nickname: String,
    val first_name: String = "",
    val last_name: String = "",
//    val position: String,
    val roles: String = "",
//    val allow_marketing: Boolean,
//    val props: Map<String, String>,
//    notify_props: UserNotifyProps,
    val last_password_update: Int = 0,
    val last_picture_update: Int = 0,
    val failed_attempts: Int = 0,
//    val locale: String,
//    val timezone: UserTimezone?,
//    val mfa_active: Boolean,
//    val mfa_secret: String,
    val last_activity_at: Int = 0
//    val is_bot: Boolean,
//    val bot_description: String,
//    val bot_last_icon_update: Int,
//    val terms_of_service_id: String,
//    val terms_of_service_create_at: Int
)