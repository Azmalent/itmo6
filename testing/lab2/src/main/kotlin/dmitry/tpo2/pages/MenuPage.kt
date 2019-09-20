package dmitry.tpo2.pages

object MenuPage : AbstractPage() {
    private const val menuItems =
         "[1] Все композиции\n" +
         "[2] Поиск\n" +
         "[3] Добавить композицию\n" +
         "[4] Удалить композицию\n" +
         "[5] Информация о композиции\n" +
         "[6] О программе\n" +
         "[Другое число] Выход"

    override val pageTitle = "Меню"

    override fun runInteractionLogic(): AbstractPage? {
        println("Введите число для выбора пункта меню: ")
        println(menuItems)
        val item = readInt()
        return when (item) {
            1 -> TrackListPage
            2 -> SearchPage
            3 -> NewTrackPage
            4 -> DeletePage
            5 -> MusicTrackInfoPage
            6 -> AboutPage
            else -> null
        }
    }
}
