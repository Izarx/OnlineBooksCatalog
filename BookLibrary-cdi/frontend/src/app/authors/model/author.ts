export class Author {
    authorId: number | null | undefined
    firstName: string
    lastName: string

    constructor(authorId: number, firstName: string, lastName: string) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
