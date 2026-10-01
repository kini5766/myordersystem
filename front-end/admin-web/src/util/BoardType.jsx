export const BoardType = {
  NOTICE: "NOTICE",
  EVENT: "EVENT",
};

export const BOARD_TYPE_LABEL = {
  NOTICE: "공지사항",
  EVENT: "행사",
};

const BOARD_TYPE_FROM_LABEL = {
  공지사항: "NOTICE",
  행사: "EVENT",
};

/** enum 값 → 한글명 */
export function toBoardTypeLabel(type) {
  return BOARD_TYPE_LABEL[type] ?? type;
}

/** 한글명 → enum 값 */
export function toBoardType(labelOrType) {
  if (BOARD_TYPE_LABEL[labelOrType]) {
    return labelOrType;
  }
  return BOARD_TYPE_FROM_LABEL[labelOrType];
}