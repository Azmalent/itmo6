package dmitry.tpo2.client

import io.kotlintest.shouldNotThrowAny
import io.kotlintest.specs.StringSpec

class DatabaseManagerTests : StringSpec({
    "Connects and creates tables" {
        shouldNotThrowAny {
            DatabaseManager.initDatabase()
        }
    }
})