export default function Loader() {
    return (
        <>
            <div className="modal-backdrop show"></div>
                <div className="position-fixed top-50 start-50 translate-middle">
                <div className="spinner-border spinner-border-lg text-dark" role="status" style={{width: '4rem', height: '4rem'}}>
                    <span className="visually-hidden">Loading...</span>
                </div>
            </div>
        </>
    );
}
