export class SortableColumn {
    name: string;
    title: string;
    direction: string;

    public constructor(name: string, title: string, direction: string) {
        this.name = name;
        this.title = title;
        this.direction = direction;
    }
}