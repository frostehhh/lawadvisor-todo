CREATE TABLE "user".account
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    name text NOT NULL,
    created_on timestamp with time zone NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE todo.todo
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    title text,
    details text,
    created_on timestamp with time zone NOT NULL DEFAULT now(),
    updated_on timestamp with time zone NOT NULL DEFAULT now(),
    deleted_on timestamp with time zone,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES "user".account (id)
);