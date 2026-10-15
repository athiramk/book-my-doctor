package com.athiramk.bookmydoctor.users;

record CreateUserCmd(String name, String email, String password, Role role) {}
