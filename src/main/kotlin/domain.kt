package lab1

data class Character(val name: String, var action: String) {
    fun moveTo(location: Location) {
        action = "$name перемещается в ${location.name}"
    }

    fun interactWith(obj: Object) {
        action = "$name взаимодействует с ${obj.name}"
        obj.changeState("использован персонажем $name")
    }
}

data class Location(val name: String, var description: String, val objects: MutableList<Object> = mutableListOf()) {
    fun addObject(obj: Object) {
        objects.add(obj)
    }

    fun removeObject(obj: Object) {
        objects.remove(obj)
    }
}

data class Object(val name: String, var state: String) {
    fun changeState(newState: String) {
        state = newState
    }
}

data class Environment(var description: String) {
    fun update(newDescription: String) {
        description = newDescription
    }
}

fun createDomainModel() {
    val zafod = Character("Зафод", "осматривается")
    val marvin = Character("Марвин", "ждёт команды")

    val cave = Location("Пещера", "Сеть галерей и переходов, заваленных обломками и потрохами")
    val depths = Environment("Сырой воздух из темных глубин, пыльный мрак")

    val whale = Object("Кит", "упал, образовав провал")
    val debris = Object("Обломки", "завалены")
    val flashlight = Object("Фонарь", "выключен")

    cave.addObject(whale)
    cave.addObject(debris)
    cave.addObject(flashlight)

    println("Персонажи: $zafod, $marvin")
    println("Локация: $cave")
    println("Окружение: $depths")
    println("Объекты: $whale, $debris, $flashlight")


    zafod.moveTo(cave)
    println(zafod.action)

    marvin.interactWith(debris)
    cave.removeObject(debris)
    println(marvin.action)
    println("Объекты: ${cave.objects}")

    zafod.interactWith(flashlight)
    flashlight.changeState("включен")
    depths.update("Освещенная пещера, мрак рассеян")
    println(zafod.action)
    println(depths.description)
}

fun main() {
    createDomainModel()
}
