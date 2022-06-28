import "./sections.scss";

interface titleProps {
    title?: string
    color?: string
}

export default (props: titleProps) => {
    return (
        <div className="d-flex align-items-center justify-content-center">
            <h1 className="title">
                <strong> {props.title} </strong>
                <hr className="title-line" style={{borderColor: props.color}} />
            </h1>
        </div>
    )
}