#
# 2020년 봄, 프로그래밍기초 과제#04
# 
# 학번 : 2020076735
# 이름 : 박민선
#
################################################################
################################################################
### 5-11. 부분 리스트
### (1) 
def drop_before(s,index):
    while s != [] and index > 0: # 0 ~ (index-1)번 원소 삭제 => index번 반복
        s, index = s[1:], index - 1
    return s

### (2)
# take_before_r : 재귀 버전
def take_before_r(s,index):
    if s != [] and index > 0:
        return [s[0]] + take_before_r(s[1:],index - 1)
    else:
        return []
    
# take_before_t : 꼬리재귀 버전
def take_before_t(s,index):
    def loop(s,index,ss):
        if s != [] and index > 0:
            return loop(s[1:],index-1,ss+[s[0]])
        else:
            return ss
    return loop(s,index,[])

# take_before_w : while 루프 버전
def take_before_w(s,index):
    ss = []
    while s != [] and index > 0:
        ss += [s[0]]
        s, index = s[1:], index - 1
    return ss

# print("take_before(s,0) =", take_before(s,0)) # []
# print("take_before(s,1) =", take_before(s,1)) # [1]
# print("take_before(s,2) =", take_before(s,2)) # [1, 2]
# print("take_before(s,3) =", take_before(s,3)) # [1, 2, 3]
# print("take_before(s,4) =", take_before(s,4)) # [1, 2, 3, 4]
# print("take_before(s,5) =", take_before(s,5)) # [1, 2, 3, 4, 5]
# print("take_before(s,6) =", take_before(s,6)) # [1, 2, 3, 4, 5]
# print("take_before([],4) =", take_before([],4)) # []
# print("take_before(s,-3) =", take_before(s,-3)) # []

### (3)
# take_before 함수는 재귀, 꼬리재귀, while 루프 중 하나를 사용한다.
def sublist(s,low,high): # low : 시작 인덱스, high : 끝 인덱스, high전까지 남김
    if low < 0: low = 0
    if high < 0: high = 0
    if low <= high:
        return drop_before(take_before_w(s,high),low)
    # take_before(s,high) => 0 ~ (high-1)번째 원소들 저장된 리스트 리턴
    # 0 ~ (high-1)번째 원소들이 저장된 리스트에서 0~(low-1)번째 원소들 삭제
    # => low ~ high-1 번째 원소들만 남음
    else:
        return []

##################################################################

cases_5_11 = (
	((0,0), []),
	((0,1), [1]),
	((0,2), [1, 2]),
	((0,3), [1, 2, 3]),
	((0,4), [1, 2, 3, 4]),
	((0,5), [1, 2, 3, 4, 5]),
	((0,6), [1, 2, 3, 4, 5]),
	((1,1), []),
	((1,2), [2]),
	((1,3), [2, 3]),
	((1,4), [2, 3, 4]),
	((1,5), [2, 3, 4, 5]),
	((1,6), [2, 3, 4, 5]),
	((2,2), []),
	((2,3), [3]),
	((2,4), [3, 4]),
	((2,5), [3, 4, 5]),
	((2,6), [3, 4, 5]),
	((3,3), []),
	((3,4), [4]),
	((3,5), [4, 5]),
	((3,6), [4, 5]),
	((5,2), []),
	((-3,-2), []),
)

fs_5_11 = (
    ("", "sublist", sublist), 
)

def test_5_11():
    point = 0
    total = 0
    s = [1,2,3,4,5]
    for (msg, fn, f) in fs_5_11:
      for ((start, end), ans) in cases_5_11:
        total += 1
        res = f(s, start, end)
        if (res == ans):
            point += 1
        else:
            print(msg, fn, end="(")
            print(start, end, sep=",", end=") = ")
            print(ans, ", your answer is", res)
    print(point, "/", total)

def test_all():
    test_5_11()

################################################################
