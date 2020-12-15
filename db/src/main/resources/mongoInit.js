const db = (new Mongo()).getDB("maritime");

//  Stavros Lamprinos [stalab at linuxmail.org] on 15/12/20
//  initialize every collection
const collections = [{
    "name": "oceanConditions",
    "initialize": {
        "bsonType": "object",
        "required": ["geoPoint", "timestamp"],
        "properties": {
            "geoPoint": {
                "bsonType": "object",
                "required": ["type", "coordinates"],
                "properties": {
                    "type": {
                        "bsonType": "string",
                        "description": "Point attiribute o geoJSON format"
                    },
                    "coordinates": {
                        "bsonType": "array",
                        "description": "Longitute and latitude of the point"
                    } 
                }
            },
            "meanWaveLength": {
                "bsonType": "number",
                "description": "mean wave length in meters"
            },
            "bottomDepth": {
                "bsonType": "double",
                "description": "bottom depth of the oceans point in meters"
            },
            "tidalEffect": {
                "bsonType": "double",
                "description": "sea surface height above sea level in meters (=>tidal effect)"
            },
            "seaHeight": {
                "bsonType": "double",
                "description": "significant height of wind and swell waves (=>see state)"
            },
            "timestamp": {
                "bsonType": "long"
            }
        }
    } 
},
{
    "name": "vessels",
    "initialize": {
        "bsonType": "object",
        "required": ["mmsi"],
        "properties": {
            "callSign": {
                "bsonType": "string",
                "description": "nternational radio call sign (max 7 characters), assigned to the vessel by its country of registry"
            },
            "country": {
                "bsonType": "string",
                "description": "The country in which the vessel belongs"
            },
            "destination": {
                "bsonType": "string",
                "description": "Destination of this trip (manually entered)"
            },
            "draught": {
                "bsonType": "double",
                "description": "Allowed values: 0.1-25.5 meters"
            },
            "eta": {
                "bsonType": "string",
                "description": "ETA (estimated time of arrival) in format dd-mm hh:mm (day, month, hour, minute) – UTC time zone"
            },
            "imo": {
                "bsonType": "number",
                "description": "IMO ship identification number (7 digits)"
            },
            "mmsi": {
                "bsonType": "number",
                "description": "The MMSI number (Maritime Mobile Service Identity)"
            },
            "vesselName": {
                "bsonType": "string",
                "description": "Name of the vessel (max 20 characters)"
            }
        }
    } 
},
{
    "name": "vesselTrajectoryPoints",
    "initialize": {
        "bsonType": "object",
        "required": ["geoPoint", "timestamp", "speed"],
        "properties": {
            "geoPoint": {
                "bsonType": "object",
                "required": ["type", "coordinates"],
                "properties": {
                    "type": {
                        "bsonType": "string",
                        "description": "Point attiribute o geoJSON format"
                    },
                    "coordinates": {
                        "bsonType": "array",
                        "description": "Longitute and latitude of the point"
                    } 
                }
            },
            "speed": {
                "bsonType": "double",
                "description": "ship's speed"
            },
            "vesselId": {
                "bsonType": "objectId",
                "description": "reference to vessel _id"
            },
            "timestamp": {
                "bsonType": "long"
            },
        }
    } 
}]

//  collection creation
collections.forEach(function (collection) {
    db.createCollection(collection.name, {
        "validator": {$jsonSchema: collection.initialize},
        "validationAction": "error",
        "validationLevel": "moderate"
    })
})
