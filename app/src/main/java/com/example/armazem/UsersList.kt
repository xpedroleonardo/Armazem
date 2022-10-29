package com.example.armazem

class UsersList {
  private var usersHash = HashMap<String, String>()

  constructor() {
    this.usersHash["Camille"] = "12345"
    this.usersHash["Keila"] = "12345"
    this.usersHash["Heloisa"] = "12345"
    this.usersHash["Kethellen"] = "12345"
    this.usersHash["Luana"] = "12345"
    this.usersHash["Lucas"] = "12345"
    this.usersHash["MateusFerreira"] = "12345"
    this.usersHash["MatheusMasuda"] = "12345"
    this.usersHash["Pedro"] = "12345"
    this.usersHash["Renan"] = "12345"
  }

  fun getUsers(): HashMap<String, String> {
    return usersHash
  }
}