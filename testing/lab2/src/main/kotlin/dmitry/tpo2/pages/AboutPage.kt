package dmitry.tpo2.pages

object AboutPage : AbstractPage() {
    override val pageTitle = "О программе"

    override fun runInteractionLogic(): AbstractPage {
        println(" - Данное ПО доступно по лицензии WTFPL\n" +
                " - Делайте чё хотите\n" +
                " - Никакие права не защищены\n")
        anykey()
        return MenuPage
    }
}
