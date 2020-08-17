package com.hungknow.states

import com.hungknow.models.UserProfile
import kotlinx.serialization.Serializable

@Serializable
data class UsersState(
        val currentUserId: String = "",
//        val isManualStatus: RelationOneToOne<UserProfile, boolean>,
//        val mySessions: Array<Session> = arrayOf(),
//        val myAudits: Array<Audit> = arrayOf(),
//        val profiles: IDMappedObjects<UserProfile>
        val profiles: Map<String, UserProfile> = mapOf()
//        val profilesInTeam: RelationOneToMany<Team, UserProfile>,
//        val profilesNotInTeam: RelationOneToMany<Team, UserProfile>,
//        val profilesWithoutTeam: Set<String>,
//        val profilesInChannel: RelationOneToMany<Channel, UserProfile>,
//        val profilesNotInChannel: RelationOneToMany<Channel, UserProfile>,
//        val profilesInGroup: RelationOneToMany<Group, UserProfile>,
//        val statuses: RelationOneToOne<UserProfile, String>,
//        val stats: RelationOneToOne<UserProfile, UsersStats>,
//        val filteredStats: UsersStats?
)