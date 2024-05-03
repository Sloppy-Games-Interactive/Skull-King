package util

trait Observer {
    def update(e:ObservableEvent): Unit
}

class ObservableEvent {}

class Observable {
  var subscribers:Vector[Observer] = Vector()
  def add(s:Observer) = subscribers = subscribers :+ s
  def remove(s:Observer) = subscribers = subscribers.filterNot(o=>o==s)
  def notifyObservers(e:ObservableEvent = ObservableEvent()) = subscribers.foreach(o => o.update(e))
}
