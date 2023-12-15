create table sfexpress_cargo_detail
(
    id         bigint auto_increment
        primary key,
    express_id bigint       null,
    name       varchar(255) null
);

create table sfexpress_express
(
    id                     bigint auto_increment
        primary key,
    cargo_desc             varchar(255) null,
    country                varchar(255) null,
    create_time            datetime(6)  null,
    dest_code              varchar(255) null,
    diliver_address        varchar(255) null,
    diliver_city           int          null,
    diliver_contact        varchar(255) null,
    diliver_mobile         int          null,
    express_type_id        int          null,
    monthly_card           varchar(255) null,
    order_id               varchar(255) null,
    origin_code            varchar(255) null,
    pay_method             int          null,
    pickup_appoint_endtime datetime(6)  null,
    send_start_tm          datetime(6)  null,
    sender_address         varchar(255) null,
    sender_city            int          null,
    sender_contact         varchar(255) null,
    sender_mobile          int          null,
    status                 int          null,
    total_height           int          null,
    total_length           int          null,
    total_volume           int          null,
    total_weight           int          null,
    total_width            int          null,
    waybill_no             varchar(255) null,
    waybill_type           int          null
);

create table sfexpress_route
(
    id             bigint auto_increment
        primary key,
    accept_address varchar(255) null,
    accept_time    datetime(6)  null,
    express_id     bigint       null,
    op_code        varchar(255) null,
    remark         varchar(255) null
);

