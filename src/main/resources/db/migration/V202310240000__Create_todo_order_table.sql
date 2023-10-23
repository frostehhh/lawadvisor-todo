CREATE TABLE todo.todo_order
(
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    prev_todo uuid,
    next_todo uuid,
    PRIMARY KEY (id),
    FOREIGN KEY (id)
        REFERENCES todo.todo (id)
);