export class ResponseData<T> {
    content: Array<T> = new Array<T>();
    totalElements: number;

    public constructor() {
    }
}