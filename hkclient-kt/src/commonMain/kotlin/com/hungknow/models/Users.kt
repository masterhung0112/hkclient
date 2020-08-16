package com.hungknow.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile (
    val id: String,
    val create_at: Int,
    val update_at: Int,
    val delete_at: Int,
    val username: String,
    val password: String,
//    val auth_data: String,
//    val auth_service: String,
    val email: String,
    val email_verified: Boolean,
//    val nickname: String,
    val first_name: String,
    val last_name: String,
//    val position: String,
    val roles: String,
//    val allow_marketing: Boolean,
//    val props: Map<String, String>,
//    notify_props: UserNotifyProps,
    val last_password_update: Int,
    val last_picture_update: Int,
    val failed_attempts: Int,
//    val locale: String,
//    val timezone: UserTimezone?,
//    val mfa_active: Boolean,
//    val mfa_secret: String,
    val last_activity_at: Int
//    val is_bot: Boolean,
//    val bot_description: String,
//    val bot_last_icon_update: Int,
//    val terms_of_service_id: String,
//    val terms_of_service_create_at: Int
)