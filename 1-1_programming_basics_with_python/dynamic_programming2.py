#
# 프로그래밍기초 과제 #05
#
# 학번 : 2020076735
# 이름 : 박민선
#
### 7-2 달나라 토끼를 위한 구매대금 지불 도우미

def change(total): # 재귀
    if total % 7 == 0:
        return total / 7
    if total % 5 == 0:
        return total / 5
    else:
        if total // 7 > 0:
            return 1 + change(total - 7)
        elif total // 5 > 0:
            return 1 + change(total - 5)
        elif total // 2 > 0:
            return 1 + change(total - 2)
        else:
            return total

# 동적 계획법
def change(total): # 1원, 2원, 5원, 7원
    minCountList = [ 0 for _ in range(total+1) ]
    if total > 0:
        for i in range(1,total+1):
            if i == 1 or i == 2 or i == 5 or i == 7:
                minCountList[i] = 1
            else:
                smallest = minCountList[i-1]
                if i > 2:
                    smallest = min(smallest,minCountList[i-2])
                if i > 5:
                    smallest = min(smallest,minCountList[i-5])
                if i > 7:
                    smallest = min(smallest,minCountList[i-7])
                minCountList[i] = smallest + 1
    else:
        return 0
    return minCountList[total]

# # 테스트코드


cases = (
    (1, 1), 
    (2, 1), 
    (3, 2), 
    (4, 2), 
    (5, 1), 
    (6, 2), 
    (7, 1), 
    (8, 2), 
    (9, 2), 
    (10, 2), 
    (11, 3), 
    (12, 2), 
    (13, 3), 
    (14, 2), 
    (15, 3), 
    (16, 3), 
    (17, 3), 
    (18, 4), 
    (19, 3), 
    (20, 4), 
    (21, 3), 
    (22, 4), 
    (23, 4), 
    (24, 4), 
    (25, 5), 
    (26, 4), 
    (27, 5), 
    (28, 4), 
)

def test():
    point = 0
    total = 0
    for (inp, ans) in cases:
        total += 1
        res = change(inp)
        if (res == ans):
            point += 1
        else:
            print("change(", inp, ") = ", sep="", end="")
            print(ans, ", your answer is", res)
    print(point, "/", total)

################################################################

test()
