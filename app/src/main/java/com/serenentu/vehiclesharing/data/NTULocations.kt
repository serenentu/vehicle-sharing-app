package com.serenentu.vehiclesharing.data

/**
 * NTU-specific locations, hotspots, and common routes
 */
object NTULocations {
    
    // NTU Campus Locations
    val CAMPUS_LOCATIONS = listOf(
        "NTU North Spine",
        "NTU South Spine",
        "Hall 1",
        "Hall 2",
        "Hall 3",
        "Hall 4",
        "Hall 5",
        "Hall 6",
        "Hall 7",
        "Hall 8",
        "Hall 9",
        "Hall 10",
        "Hall 11",
        "Hall 12",
        "Hall 13",
        "Hall 14",
        "Hall 15",
        "Hall 16",
        "Pioneer Hall",
        "Crescent Hall",
        "Tamarind Hall",
        "LT1 (Lee Wee Nam Library)",
        "LT2 (Lee Wee Nam Library)",
        "LT19 (North Spine)",
        "LT20 (North Spine)",
        "LT21 (North Spine)",
        "LT22 (North Spine)",
        "LT23 (North Spine)",
        "LT24 (North Spine)",
        "LT25 (North Spine)",
        "LT26 (North Spine)",
        "LT27 (North Spine)",
        "Lee Kong Chian School of Medicine (LKC)",
        "School of Art, Design and Media (ADM)",
        "NTU Sports & Recreation Centre",
        "The Hive (Learning Hub)",
        "Nanyang Business School",
        "School of Electrical & Electronic Engineering (EEE)",
        "School of Computer Science and Engineering (SCSE)",
        "School of Mechanical & Aerospace Engineering (MAE)",
        "School of Civil & Environmental Engineering (CEE)",
        "Canteen A",
        "Canteen 2 (North Hill)",
        "Canteen 9",
        "Canteen 11",
        "Canteen 13",
        "Canteen 14",
        "Canteen 16",
        "Quad Cafe",
        "Food Court (North Spine)",
        "Koufu (North Spine)",
        "NTU Innovation Centre",
        "NTU Main Gate (Nanyang Avenue)",
        "NTU Campus Centre"
    )
    
    // Singapore-wide common destinations
    val SINGAPORE_LOCATIONS = listOf(
        // East
        "Tampines",
        "Pasir Ris",
        "Bedok",
        "Changi Airport",
        "East Coast Park",
        "Simei",
        "Tanah Merah",
        // Central
        "Orchard Road",
        "Marina Bay",
        "City Hall",
        "Raffles Place",
        "Bugis",
        "Dhoby Ghaut",
        "Somerset",
        "Clarke Quay",
        "Marina Bay Sands",
        "Gardens by the Bay",
        // West
        "Jurong East",
        "Jurong West",
        "Boon Lay",
        "Choa Chu Kang",
        "Bukit Batok",
        "Clementi",
        "Bukit Gombak",
        // North
        "Woodlands",
        "Yishun",
        "Sembawang",
        "Admiralty",
        "Causeway Point",
        "Ang Mo Kio",
        "Yio Chu Kang",
        // Northeast
        "Serangoon",
        "Hougang",
        "Punggol",
        "Sengkang",
        "Kovan",
        // Others
        "Queenstown",
        "Tiong Bahru",
        "Holland Village",
        "Buona Vista",
        "one-north",
        "Kent Ridge",
        "Vivo City",
        "Sentosa"
    )
    
    // Combined list for autocomplete
    val ALL_LOCATIONS = (CAMPUS_LOCATIONS + SINGAPORE_LOCATIONS).sorted()
    
    // Common routes with time-based suggestions
    data class CommonRoute(
        val origin: String,
        val destination: String,
        val description: String,
        val suggestedTimes: List<String>
    )
    
    val COMMON_ROUTES = listOf(
        CommonRoute(
            "NTU",
            "Tampines",
            "NTU → East Side (Tampines)",
            listOf("Friday 6pm", "Saturday Morning", "Sunday Evening")
        ),
        CommonRoute(
            "NTU",
            "Changi Airport",
            "NTU → Changi Airport",
            listOf("Early Morning", "Late Evening", "Weekend")
        ),
        CommonRoute(
            "NTU",
            "Orchard Road",
            "NTU → Orchard Shopping",
            listOf("Friday Evening", "Saturday Afternoon", "Sunday")
        ),
        CommonRoute(
            "NTU",
            "Jurong East",
            "NTU → Jurong East MRT/JEM",
            listOf("Weekday Evening", "Weekend")
        ),
        CommonRoute(
            "NTU",
            "Marina Bay",
            "NTU → Marina Bay/CBD",
            listOf("Weekday Morning", "Weekend")
        ),
        CommonRoute(
            "NTU",
            "Woodlands",
            "NTU → North (Woodlands/Causeway)",
            listOf("Friday Evening", "Saturday", "Sunday Evening")
        ),
        CommonRoute(
            "NTU",
            "Sengkang",
            "NTU → Northeast (Sengkang/Punggol)",
            listOf("Friday Evening", "Weekend")
        ),
        CommonRoute(
            "Tampines",
            "NTU",
            "East Side → NTU",
            listOf("Sunday Evening", "Monday Morning")
        ),
        CommonRoute(
            "Changi Airport",
            "NTU",
            "Changi Airport → NTU",
            listOf("Evening", "Late Night")
        ),
        CommonRoute(
            "Orchard Road",
            "NTU",
            "Orchard → NTU",
            listOf("Sunday Evening")
        )
    )
    
    // NTU Halls list for profile
    val HALLS = listOf(
        "Hall 1",
        "Hall 2",
        "Hall 3",
        "Hall 4",
        "Hall 5",
        "Hall 6",
        "Hall 7",
        "Hall 8",
        "Hall 9",
        "Hall 10",
        "Hall 11",
        "Hall 12",
        "Hall 13",
        "Hall 14",
        "Hall 15",
        "Hall 16",
        "Pioneer Hall",
        "Crescent Hall",
        "Tamarind Hall"
    )
    
    // Course cohorts
    val COURSE_COHORTS = listOf(
        "EEE Year 1",
        "EEE Year 2",
        "EEE Year 3",
        "EEE Year 4",
        "CS Year 1",
        "CS Year 2",
        "CS Year 3",
        "CS Year 4",
        "MAE Year 1",
        "MAE Year 2",
        "MAE Year 3",
        "MAE Year 4",
        "CEE Year 1",
        "CEE Year 2",
        "CEE Year 3",
        "CEE Year 4",
        "MSE Year 1",
        "MSE Year 2",
        "MSE Year 3",
        "MSE Year 4",
        "Business Year 1",
        "Business Year 2",
        "Business Year 3",
        "Business Year 4",
        "ADM Year 1",
        "ADM Year 2",
        "ADM Year 3",
        "ADM Year 4",
        "Medicine Year 1",
        "Medicine Year 2",
        "Medicine Year 3",
        "Medicine Year 4",
        "Medicine Year 5"
    )
    
    // NTU Emergency and Security Contact
    const val NTU_SECURITY_HOTLINE = "67911616"
    const val NTU_EMERGENCY_INFO = "NTU Security: 6791 1616"
}
