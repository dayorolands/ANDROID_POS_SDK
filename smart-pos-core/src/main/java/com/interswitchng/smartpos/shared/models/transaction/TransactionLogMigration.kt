package com.interswitchng.smartpos.shared.models.transaction

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

class TransactionLogMigration: RealmMigration {

    // perform migration for the specific schema version update
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        // start from the earliest schema version
        var migratingVersion = oldVersion

        // get the schema for the transaction Log class
        val logSchema = realm.schema.get(TransactionLog::class.java.simpleName)


        if  (migratingVersion == 0L) {
            // migrating schema from 0 to 1 with field cardHolderName
            logSchema
                ?.addField("cardHolderName", String::class.java, FieldAttribute.REQUIRED)
//                ?.setRequired("cardHolderName", false)

            // increment old version to update other fields
            migratingVersion++
        }

        if  (migratingVersion == 1L) {
            // migrating schema from 0 to 1 with field cardHolderName
            logSchema
                    ?.addField("transactionId", String::class.java, FieldAttribute.REQUIRED)
                   // ?.setRequired("transactionId", true)
            // increment old version to update other fields
            migratingVersion++
        }

        if (migratingVersion == 2L) {
            // migrating schema from 1 to 2 with field cardHolderName
            logSchema
                    ?.addField("additionalInfo", String::class.java, FieldAttribute.REQUIRED)
            // increment old version to update other fields
            migratingVersion++
        }

        if (migratingVersion == 3L) {
            // perform migration from 2 to 3
            print("Migration Done")
        }
    }
}