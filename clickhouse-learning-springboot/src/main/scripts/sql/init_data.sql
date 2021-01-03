-- insert into bitmap_test.label_string_granularity_1
INSERT INTO bitmap_test.label_string_granularity_1 (dayno, labelname, labelvalue, uv) VALUES ('2020-11-25', 'gender', 'M', bitmapBuild([1, 3, 5, 7, 9]));
INSERT INTO bitmap_test.label_string_granularity_1 (dayno, labelname, labelvalue, uv) VALUES ('2020-11-25', 'gender', 'F', bitmapBuild([2, 4, 6, 8, 10]));
INSERT INTO bitmap_test.label_string_granularity_1 (dayno, labelname, labelvalue, uv) VALUES ('2020-11-25', 'age_group', '1-10', bitmapBuild([1, 2, 3]));
INSERT INTO bitmap_test.label_string_granularity_1 (dayno, labelname, labelvalue, uv) VALUES ('2020-11-25', 'age_group', '11-20', bitmapBuild([4, 5, 6]));
INSERT INTO bitmap_test.label_string_granularity_1 (dayno, labelname, labelvalue, uv) VALUES ('2020-11-25', 'age_group', '21-30', bitmapBuild([7, 8, 9]));
