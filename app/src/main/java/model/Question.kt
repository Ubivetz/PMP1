package model

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String
)

object QuestionGenerator {
    fun generateQuestions(count: Int): List<Question> {
        val allQuestions = listOf(
            Question("Яка столиця України?", listOf("Київ", "Львів", "Одеса", "Харків"), "Київ"),
            Question("Скільки континентів у світі?", listOf("5", "6", "7", "8"), "7"),
            Question("Яка найбільша планета Сонячної системи?", listOf("Юпітер", "Марс", "Земля", "Сатурн"), "Юпітер"),
            Question("Скільки секунд у одній хвилині?", listOf("60", "50", "100", "120"), "60"),
            Question("Яке найбільше море в світі?", listOf("Саргасове", "Чорне", "Філіппінське", "Середземне"), "Філіппінське"),
            Question("Хто написав «Кобзар»?", listOf("Тарас Шевченко", "Іван Франко", "Леся Українка", "Микола Гоголь"), "Тарас Шевченко"),
            Question("Яка найвища гора у світі?", listOf("Еверест", "Кіліманджаро", "Канченджанга", "Монблан"), "Еверест"),
            Question("Скільки гравців у футбольній команді на полі?", listOf("11", "10", "12", "9"), "11"),
            Question("Яке місто називають «вічним містом»?", listOf("Рим", "Афіни", "Париж", "Каїр"), "Рим"),
            Question("Хто першим здійснив подорож навколо світу?", listOf("Фернан Магеллан", "Христофор Колумб", "Джеймс Кук", "Америго Веспуччі"), "Фернан Магеллан"),
            Question("Яка найдовша річка у світі?", listOf("Амазонка", "Ніл", "Міссісіпі", "Янцзи"), "Амазонка"),
            Question("Який острів є найбільшим у світі?", listOf("Гренландія", "Нова Гвінея", "Каліфорнія", "Мадагаскар"), "Гренландія"),
            Question("Як називається найбільший океан?", listOf("Тихий", "Атлантичний", "Індійський", "Північний Льодовитий"), "Тихий"),
            Question("Яка країна має найбільшу кількість населення?", listOf("Китай", "Індія", "США", "Індонезія"), "Китай"),
            Question("Яка валюта є в Японії?", listOf("Єна", "Долар", "Фунт", "Євро"), "Єна"),
            Question("Хто створив теорію відносності?", listOf("Ісаак Ньютон", "Альберт Ейнштейн", "Нікола Тесла", "Галілео Галілей"), "Альберт Ейнштейн"),
            Question("Яка релігія є найпоширенішою у світі?", listOf("Християнство", "Іслам", "Буддизм", "Індуїзм"), "Християнство"),
            Question("Яка країна є батьківщиною картоплі?", listOf("Перу", "Мексика", "Італія", "Канада"), "Перу"),
            Question("Хто є автором роману «1984»?", listOf("Джордж Орвелл", "Френсіс Фіцджеральд", "Стівен Кінг", "Вільям Шекспір"), "Джордж Орвелл"),
            Question("Який хімічний елемент має символ «O»?", listOf("Кисень", "Азот", "Вуглець", "Гідроген"), "Кисень"),
            Question("Яка країна є найбільшим виробником шоколаду?", listOf("Швейцарія", "Бельгія", "Німеччина", "Кот-д'Івуар"), "Швейцарія")
        )
        return allQuestions.shuffled().take(count).map { question ->
            val shuffledAnswers = question.answers.shuffled()
            question.copy(answers = shuffledAnswers)
        }
    }
}