package com.example.armazem

class UsersList {
  private var usersHash = HashMap<String, String>()

  constructor() {
    this.usersHash.put("Camille", "12345")
    this.usersHash.put("Keila", "12345")
    this.usersHash.put("Heloisa", "12345")
    this.usersHash.put("Kethellen", "12345")
    this.usersHash.put("Luana", "12345")
    this.usersHash.put("Lucas", "12345")
    this.usersHash.put("MateusFerreira", "12345")
    this.usersHash.put("MatheusMasuda", "12345")
    this.usersHash.put("Pedro", "12345")
    this.usersHash.put("Renan", "12345")
  }

  fun getUsers(): HashMap<String, String> {
    return usersHash
  }
}