package dmitry.tpo2.pages

object MenuPage : AbstractPage() {
    private const val menuItems =
            """[1] Все композиции
            [2] Поиск
            [3] Добавить композицию
            [4] Удалить композицию
            [5] Информация о композиции
            [6] О программе
            [Другое число] Выход"""

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
