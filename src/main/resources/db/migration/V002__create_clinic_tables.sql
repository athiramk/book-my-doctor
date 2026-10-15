create sequence clinic_schedule_id_seq start with 100 increment by 50;

create table clinic_schedules
(
    id                       bigint       not null default nextval('clinic_schedule_id_seq'),
    doctor_name              text         not null,
    specialization           text         not null,
    clinic_address           text         not null,
    max_tokens_per_day       integer      not null default 8,
    start_time               time         not null default '19:30:00',
    end_time                 time         not null default '21:30:00',
    active_days              text         not null default 'MONDAY,WEDNESDAY,FRIDAY',
    booking_open_time        time         not null default '18:00:00',
    cancellation_cutoff_time time         not null default '17:30:00',
    created_at               timestamp    not null default CURRENT_TIMESTAMP,
    updated_at               timestamp,
    version                  bigint       not null default 0,
    primary key (id)
);

create sequence daily_session_id_seq start with 100 increment by 50;

create table daily_sessions
(
    id                           bigint       not null default nextval('daily_session_id_seq'),
    session_date                 date         not null,
    status                       text         not null default 'OPEN',
    current_serving_token_number  integer      not null default 0,
    booking_opens_at             timestamp    not null,
    booking_closes_at            timestamp    not null,
    cancellation_closes_at       timestamp    not null,
    max_tokens                   integer      not null default 8,
    notes                        text,
    created_at                   timestamp    not null default CURRENT_TIMESTAMP,
    updated_at                   timestamp,
    version                      bigint       not null default 0,
    primary key (id),
    constraint unique_session_date unique (session_date)
);

create sequence token_booking_id_seq start with 100 increment by 50;

create table token_bookings
(
    id                  bigint       not null default nextval('token_booking_id_seq'),
    session_id          bigint       not null,
    token_number        integer      not null,
    booking_reference   text         not null,
    cancellation_token  text         not null,
    patient_name        text         not null,
    phone               text         not null,
    age                 integer      not null,
    visit_type          text         not null,
    symptoms            text,
    status              text         not null default 'BOOKED',
    is_walk_in          boolean      not null default false,
    booked_at           timestamp    not null default CURRENT_TIMESTAMP,
    cancelled_at        timestamp,
    created_at          timestamp    not null default CURRENT_TIMESTAMP,
    updated_at          timestamp,
    version             bigint       not null default 0,
    primary key (id),
    constraint fk_token_booking_session foreign key (session_id) references daily_sessions (id) on delete cascade,
    constraint unique_cancellation_token unique (cancellation_token),
    constraint unique_booking_reference unique (booking_reference)
);

create index idx_token_bookings_session_id on token_bookings (session_id);
create index idx_daily_sessions_date on daily_sessions (session_date);
create index idx_daily_sessions_status on daily_sessions (status);

create unique index idx_unique_active_session_phone 
    on token_bookings (session_id, phone) 
    where status != 'CANCELLED';

create unique index idx_unique_active_session_token 
    on token_bookings (session_id, token_number) 
    where status != 'CANCELLED';

-- Seed doctor user
insert into users (id, email, password, name, role, created_at)
values (3, 'dr.mathew@clinic.local', '$2a$10$otgJlzG8USgCmnvsQmQeUu1Cy1yOgUJa4s8PIEj3WO4HObYdz29JK', 'Dr. Mathew', 'ROLE_DOCTOR', CURRENT_TIMESTAMP);

-- Seed default clinic schedule
insert into clinic_schedules (id, doctor_name, specialization, clinic_address, max_tokens_per_day, start_time, end_time, active_days, booking_open_time, cancellation_cutoff_time)
values (1, 'Dr. Mathew', 'MD, DM (Neurology)', 'Village Clinic, Near Town Hall', 8, '19:30:00', '21:30:00', 'MONDAY,WEDNESDAY,FRIDAY', '18:00:00', '17:30:00');
