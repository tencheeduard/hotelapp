export class Account {

    username : string

    firstName: string

    lastName : string

    password : string

    email : string

    phoneNumber : string

    joinDate : Date

    constructor(username : string, firstName: string, lastName : string, password : string, email : string, phoneNumber : string, joinDate : Date)
    {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
    }

}