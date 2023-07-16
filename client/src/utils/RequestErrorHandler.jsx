export default function RequestErrorHandler(error) {
    if (error.request) {
        return {
            data: null,
            status: error.request.status,
            message: error.message,
        }
    }
    return error;
}
