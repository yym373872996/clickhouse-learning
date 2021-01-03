-- create database bitmap_test
CREATE DATABASE IF NOT EXISTS bitmap_test;

-- create table bitmap_test.label_string_granularity_1
CREATE TABLE bitmap_test.label_string_granularity_1
(
    `dayno` String,
    `labelname` String,
    `labelvalue` String,
    `uv` AggregateFunction(groupBitmap, UInt64)
)
ENGINE = AggregatingMergeTree()
PARTITION BY dayno
ORDER BY (labelname, labelvalue)
SETTINGS index_granularity = 1;

-- create table bitmap_test.label_int_granularity_1
CREATE TABLE bitmap_test.label_int_granularity_1
(
    `dayno` String,
    `labelname` String,
    `labelvalue` Int32,
    `uv` AggregateFunction(groupBitmap, UInt64)
)
ENGINE = AggregatingMergeTree()
PARTITION BY dayno
ORDER BY (labelname, labelvalue)
SETTINGS index_granularity = 1;
