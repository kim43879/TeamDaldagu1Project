CREATE OR REPLACE PROCEDURE update_user_grades AS
BEGIN
    -- 1. ����� ���� ���ݾ� ������Ʈ
    UPDATE user_table u
    SET u.monthly_spending = (
        SELECT COALESCE(SUM(og.price), 0)
        FROM order_goods_table og
        JOIN order_table o ON og.order_idx = o.order_idx
        WHERE o.user_idx = u.user_idx
          AND o.order_date >= TRUNC(SYSDATE, 'MONTH') - INTERVAL '1' MONTH
          AND o.order_date < TRUNC(SYSDATE, 'MONTH')
    );

    -- 2. ����� ��� ������Ʈ
    UPDATE user_table
    SET membership_idx = CASE
        -- �±� ����: ���ݾ׿� �´� ������� ����
        WHEN monthly_spending >= 1000000 AND membership_idx = 3 THEN 4   -- 3 -> 4 (Silver -> Gold)
        WHEN monthly_spending >= 500000 AND membership_idx = 2 THEN 3    -- 2 -> 3 (Bronze -> Silver)
        WHEN monthly_spending >= 100000 AND membership_idx = 1 THEN 2    -- 1 -> 2 (Basic -> Bronze)
        
        -- �϶� ����: ���� �̴� + ���� �Ⱓ ����
        WHEN monthly_spending < 1000000 AND membership_idx = 4 AND downgrade_warning = 1 THEN 3 -- 4 -> 3 (Gold -> Silver)
        WHEN monthly_spending < 500000 AND membership_idx = 3 AND downgrade_warning = 1 THEN 2  -- 3 -> 2 (Silver -> Bronze)
        WHEN monthly_spending < 100000 AND membership_idx = 2 AND downgrade_warning = 1 THEN 1  -- 2 -> 1 (Bronze -> Basic)
        ELSE membership_idx
    END,
    downgrade_warning = CASE
        -- ���� ���� ����
        WHEN monthly_spending < 1000000 AND membership_idx = 4 AND downgrade_warning = 0 THEN 1  -- Gold -> warning
        WHEN monthly_spending < 500000 AND membership_idx = 3 AND downgrade_warning = 0 THEN 1   -- Silver -> warning
        WHEN monthly_spending < 100000 AND membership_idx = 2 AND downgrade_warning = 0 THEN 1   -- Bronze -> warning
        ELSE 0
    END
    WHERE SYSDATE > last_updated + INTERVAL '3' MONTH;

    COMMIT;
END update_user_grades;