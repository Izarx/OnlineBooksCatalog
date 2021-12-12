import {SortingDirection} from "./sorting-direction";

export class SortableColumn {
    name: string;
    title?: string;
    direction: SortingDirection;

    public constructor(name: string, title: string, direction: SortingDirection) {
        this.name = name;
        this.title = title;
        this.direction = direction;
    }

    public toggleDirection() {
        if (this.direction == SortingDirection.DESC) {
            this.direction = null;
        } else if (this.direction == SortingDirection.ASC) {
            this.direction = SortingDirection.DESC;
        } else {
            this.direction = SortingDirection.ASC;
        }
    }
}